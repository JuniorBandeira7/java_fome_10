import { Api } from "../ApiConfig"
import { ApiErrorException } from "../ApiErrorException"


export interface IProduto {
    id: number,
    name: string,
    price: number,
    qtd: number
}

// Omito o id pois ele é preenchido automaticamente no banco de dados.
const criar = async (produto: Omit<IProduto, 'id'>): Promise<IProduto | ApiErrorException> => {
    try {
        const { data } = await Api().post(`/produto`, produto)
        return data
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao criar transação.')
    }
}

const obterPorId = async (id: number): Promise<IProduto | ApiErrorException> => {
    try {
        const { data } = await Api().get(`/produto/${id}`)
        return data
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao consultar API.')
    }
}

const buscarTodas = async (): Promise<IProduto[] | ApiErrorException> => {
    try {
        const { data } = await Api().get('/produto')
        return data
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao consultar API.')
    }
}

const deletar = async(id: number): Promise<undefined | ApiErrorException> => {
    try {
        await Api().delete(`/produto/${id}`)
        return undefined
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao deletar produto.')
    }
}

const atualizar = async(id: number, produto: Omit<IProduto, 'id'>): Promise<IProduto | ApiErrorException> => {
    try {
        const { data } = await Api().patch(`/produto/${id}`, produto)
        return data
    } catch (error: any) {
        return new ApiErrorException(error.response.data || 'Erro ao atualizar produto.')
    }
}

export const ProdutoService = {
    criar,
    obterPorId,
    buscarTodas,
    deletar,
    atualizar
}