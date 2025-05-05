import { useCallback, useState } from "react"
import { IPessoa, PessoasService } from '../../services/api/pessoas/PessoasService'
import { ApiErrorException } from '../../services/api/ApiErrorException'
import { useNavigate } from 'react-router-dom'
import styles from './Login.module.css';

export const Login = () => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate()

    const handleLogin = useCallback(() => {
        if (!email || !password) {
            alert('Todos os campos são obrigatórios!')
            return
        }

        const user: Omit<IPessoa, 'id' | 'name' | 'role'> = {
          email,
          password
        }

        PessoasService.login(user)
          .then((result: { message: string; userId: number }) => {
              if (result instanceof ApiErrorException) {
                  alert(`${result.message}`);
              } else {
                navigate('/')
              }
          })
    }, [email, password])

    const handleCadastro = useCallback(() =>{
      navigate(`/cadastrar`)
    }, [])
    
    return (
      <>
        <main className="">
          <section>
            <h2>LOGOS</h2>
            <form id="section-div">
              <label htmlFor="exampleInputEmail1" className={styles["form-label"]}>Email</label>
              <input type="email" className={styles.escrever} id="exampleInputEmail1" aria-describedby="emailHelp" value={email} onChange={e => setEmail(e.target.value)} />
              <label htmlFor="exampleInputPassword1" className={styles["form-label"]}>Senha</label>
              <input type="password" className={styles.escrever} id="exampleInputPassword1" value={password} onChange={e => setPassword(e.target.value)}/>
              <div id={styles.divbotao}>
                <input type="button" name="botao" className={styles.botao} onClick={handleLogin} value="Entrar" />
                <input type="button" name="botao" className={styles.botao} onClick={handleCadastro} value="Cadastrar" />
              </div>
            </form>
          </section>
        </main>
      </>
    )
}