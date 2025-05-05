import { Api } from "../ApiConfig"
import { ApiErrorException } from "../ApiErrorException"

export interface IPessoa {
    id: number
    name: string
    email: string
    password: string
    role: string
}


// Omito o id pois ele é preenchido automaticamente no banco de dados.
const create = async (dataToCreate: Omit<IPessoa, 'id' | 'role'>): Promise<any | ApiErrorException> => {
    localStorage.removeItem("token")
    try{
        const { data } = await Api().post('/usuario', dataToCreate)
        if (data.token) {
            localStorage.setItem('token', data.token)
        } else {
            throw new Error('Token não recebido')
        }

        return data
    } catch(error: any){
        return new ApiErrorException(error.message || "Erro ao criar usuário")
    } 
}

const login = async (dataToCreate: Omit<IPessoa, 'id' | 'name' | 'role'>): Promise<any | ApiErrorException> => {
    localStorage.removeItem("token")
    try{
        const { data } = await Api().post('/autenticacao/login', dataToCreate)
        if (data) {
            console.log(data)
            localStorage.setItem('token', data)
        } else {
            throw new Error('Token não recebido')
        }

        return data
    } catch(error: any){
        const status = error.response?.status
        if (status === 401) {
            return new ApiErrorException("Credenciais incorretas");
        }
        return new ApiErrorException(error.message || "Erro ao logar")
    } 
}

const checkUser = async (): Promise<any | ApiErrorException> =>{
    try{
        const { data } = await Api().get('/autenticacao')
        if (data) {
            return data
        } else {
            throw new Error('Acesso negado')
        }
    } catch(error: any){
        return new ApiErrorException(error.message || "Acesso negado")
    } 
}

const atualizar = async (id: number, pessoa: Omit<IPessoa, 'id'>): Promise<IPessoa | ApiErrorException> => {
    try {
        const { data } = await Api().patch(`/usuario/${id}`, pessoa)
        return data
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao atualizar pessoa.')
    }
}

const deletar = async (id: number): Promise<undefined | ApiErrorException> => {
    try {
        await Api().delete(`/usuario/${id}`)
        return undefined
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao deletar pessoa.')
    }
}

const buscarTodas = async (): Promise<IPessoa[] | ApiErrorException> => {
    try {
        const { data } = await Api().get('/usuario')
        return data
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao consultar API.')
    }
}

const updateRole = async (id: number, pessoa: Omit<IPessoa, 'id' | 'name' | 'email' | 'password'>): Promise<IPessoa | ApiErrorException> => {
    try {
        const { data } = await Api().patch(`/usuario/role/${id}`, pessoa)
        return data
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao atualizar pessoa.')
    }
}

export const PessoasService = {
    create,
    atualizar,
    deletar,
    buscarTodas,
    updateRole,
    login,
    checkUser
}