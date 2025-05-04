import { useCallback, useState } from "react"
//import { IUser, UsersService } from '../../services/api/Users/UsersService'
//import { ErrorException } from '../../services/api/ErrorException'
import { useNavigate } from 'react-router-dom'
import "./login.css"

export const Login = () => {
    const [email, setEmail] = useState('') // essa string vazia é o que vem renderizado por princípio
    const [password, setPassword] = useState('')
    const navigate = useNavigate()

//    const handleLogin = useCallback(() => {
//        if (!email || !password) {
//            alert('Todos os campos são obrigatórios!')
//            return
//        }

//        const user: Omit<IUser, 'id' | 'name'> = {
//          email,
//          password
//        }

//        UsersService.login(user)
//          .then((result: { message: string; userId: number }) => {
//              if (result instanceof ErrorException) {
//                  alert(result.message);
//              } else {
//                navigate(`/${result.userId}`)
//              }
 //         })
//    }, [email, password])

    const handleCadastro = useCallback(() =>{
      navigate(`/cadastro`)
    }, [])
    
    return (
      <>
        <main className="">
          <section>
            <h2>LOGOS</h2>
            <form id="section-div">
              <label htmlFor="exampleInputEmail1" className="form-label">Email</label>
              <input type="email" className="escrever" id="exampleInputEmail1" aria-describedby="emailHelp" value={email} onChange={e => setEmail(e.target.value)} />
              <label htmlFor="exampleInputPassword1" className="form-label">Senha</label>
              <input type="password" className="escrever" id="exampleInputPassword1" value={password} onChange={e => setPassword(e.target.value)}/>
              <div id="divbotao">
                <input type="button" name="botao" className="botao" /*onClick={handleLogin*/ value="Entrar" />
                <input type="button" name="botao" className="botao" onClick={handleCadastro} value="Cadastrar" />
              </div>
            </form>
          </section>
        </main>
      </>
    )
}