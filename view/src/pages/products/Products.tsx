import { useEffect, useState } from "react"
import { IProduto, ProdutoService } from "../../services/api/produtos/ProdutoService"
import { ApiErrorException } from '../../services/api/ApiErrorException'

export const Produtcs = () => {
    const [listaProdutos, setListaProdutos] = useState<IProduto[]>([])
    const [editandoId, setEditandoId] = useState<number | null>(null)
    const [produtoEditado, setProdutoEditado] = useState<IProduto | null>(null)

    useEffect(() => {
        ProdutoService.buscarTodas()
            .then((result) => {
                if (result instanceof ApiErrorException) {
                    alert(result.message)
                } else {
                    setListaProdutos(result)
                }
            })
    }, [])

    const handleEditar = (produto: IProduto) => {
        setEditandoId(produto.id)
        setProdutoEditado({ ...produto })
    }

    const handleSalvar = () => {
        if (produtoEditado) {
            ProdutoService.atualizar(produtoEditado.id, produtoEditado)
                .then((result) => {
                    if (result instanceof ApiErrorException) {
                        alert(result.message)
                    } else {
                        setListaProdutos(prev =>
                            prev.map(p =>
                                p.id === editandoId ? { ...p, ...produtoEditado } as IProduto : p
                            )
                        )
                    }
                })
            setEditandoId(null)
        }
    }

    const handleChange = (campo: keyof IProduto, valor: string) => {
        if (produtoEditado) {
            setProdutoEditado({ ...produtoEditado, [campo]: valor })
        }
    }

    const handleRemover = (produto: IProduto) => {
        if (produto) {
            ProdutoService.deletar(produto.id)
                .then((result) => {
                    if (result instanceof ApiErrorException) {
                        alert(result.message)
                        console.log(result.message)
                    } else {
                        window.location.reload()
                    }
                })
        }
    }

    return (
        <section>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Preço</th>
                        <th>Quantidade</th>
                    </tr>
                </thead>
                <tbody>
                    {listaProdutos.map(produto => (
                        <tr key={produto.id}>
                            <th scope="row">{produto.id}</th>
                            <td>
                                {editandoId === produto.id ? (
                                    <input
                                        value={produtoEditado?.name || ''}
                                        onChange={(e) => handleChange('name', e.target.value)}
                                    />
                                ) : (
                                    produto.name
                                )}
                            </td>
                            <td>
                                {editandoId === produto.id ? (
                                    <input
                                        value={produtoEditado?.price || ''}
                                        onChange={(e) => handleChange('price', e.target.value)}
                                    />
                                ) : (
                                    produto.price
                                )}
                            </td>
                            <td>
                                {(editandoId === produto.id) ? (
                                    <input
                                        value={produtoEditado?.qtd || ''}
                                        onChange={(e) => handleChange('qtd', e.target.value)}
                                    />
                                ) : (
                                    produto.qtd
                                )}
                            </td>
                            <td>
                                {editandoId === produto.id ? (
                                    <button onClick={handleSalvar}>Salvar</button>
                                ) : (
                                    <button onClick={() => handleEditar(produto)}>Editar</button>
                                )}
                            </td>
                            <td><button onClick={() => handleRemover(produto)}>Remover</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    )
}