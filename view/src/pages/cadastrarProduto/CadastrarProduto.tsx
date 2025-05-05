import { useCallback, useState } from "react";
import { ProdutoService } from '../../services/api/produtos/ProdutoService';
import { ApiErrorException } from '../../services/api/ApiErrorException';
import { useNavigate } from 'react-router-dom'

export const CadastrarProduto = () => {
    const [price, setPrice] = useState<number>();
    const [qtd, setQtd] = useState<number>();
    const [name, setName] = useState('');
    const navigate = useNavigate()

    const handleEnviar = useCallback(() => {
        if (! name || !price || !qtd) {
            alert('Todos os campos são obrigatórios!');
            return;
        }

        ProdutoService.criar({ name, price, qtd })
            .then(result => {
                if (result instanceof ApiErrorException) {
                    alert(result.message);
                } else {
                    alert('Produto criado com sucesso!');
                    navigate(`/`)
                }
            })
    }, [price, qtd, name]);

    return(
        <>
            <h1>Cadastro</h1>
            <section>
                <form id="section-div">
                    <label htmlFor="name" className="form-label">Nome</label>
                    <input type="name" className="escrever" id="name" value={name} onChange={e => setName(e.target.value)}/>
                    <label htmlFor="exampleInputEmail1" className="form-label">Quantidade</label>
                    <input type="number" className="escrever" id="exampleInputEmail1" value={qtd} onChange={e => setQtd(Number(e.target.value))}/>
                    <label htmlFor="exampleInputPassword1" className="form-label">Preço</label>
                    <input type="number" className="escrever" id="exampleInputPassword1" value={price} onChange={e => setPrice(Number(e.target.value))}/>
                    <div id="divbotao">
                        <input type="button" name="botao" className="botao" onClick={handleEnviar} value="Cadastrar" />
                    </div>
                </form>
            </section>
        </>
    )
}