import { useCallback, useState } from "react";
import { PessoasService } from '../../services/api/pessoas/PessoasService';
import { ApiErrorException } from '../../services/api/ApiErrorException';
import { useNavigate } from 'react-router-dom'

export const Cadastrar = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const navigate = useNavigate()

    const handleEnviar = useCallback(() => {
        if (!email || !password || !name) {
            alert('Todos os campos são obrigatórios!');
            return;
        }

        PessoasService.create({ name, email, password })
            .then(result => {
                if (result instanceof ApiErrorException) {
                    alert(result.message);
                } else {
                    alert('Usuário criado com sucesso!');
                    navigate(`/`)
                }
            })
    }, [email, password, name]);

    return(
        <>
            <h1>Cadastro</h1>
            <section>
                <form id="section-div">
                    <label htmlFor="name" className="form-label">Nome</label>
                    <input type="name" className="escrever" id="name" value={name} onChange={e => setName(e.target.value)}/>
                    <label htmlFor="exampleInputEmail1" className="form-label">Email</label>
                    <input type="email" className="escrever" id="exampleInputEmail1" aria-describedby="emailHelp" value={email} onChange={e => setEmail(e.target.value)}/>
                    <label htmlFor="exampleInputPassword1" className="form-label">Senha</label>
                    <input type="password" className="escrever" id="exampleInputPassword1" value={password} onChange={e => setPassword(e.target.value)}/>
                    <div id="divbotao">
                        <input type="button" name="botao" className="botao" onClick={handleEnviar} value="Cadastrar" />
                    </div>
                </form>
            </section>
        </>
    )
}