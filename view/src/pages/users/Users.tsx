import { useEffect, useState } from "react"
import { IPessoa, PessoasService } from "../../services/api/pessoas/PessoasService"
import { ApiErrorException } from '../../services/api/ApiErrorException'

export const Users = () => {
    const [listaPessoas, setListaPessoas] = useState<IPessoa[]>([])
    const [editandoId, setEditandoId] = useState<number | null>(null)
    const [pessoaEditada, setPessoaEditada] = useState<IPessoa | null>(null)
    const [editandoRole, setEditandoRole] = useState<boolean>(false)

    useEffect(() => {
        PessoasService.buscarTodas()
            .then((result) => {
                if (result instanceof ApiErrorException) {
                    alert(result.message)
                } else {
                    setListaPessoas(result)
                }
            })
    }, [])

    const handleEditar = (pessoa: IPessoa) => {
        setEditandoId(pessoa.id)
        setPessoaEditada({ ...pessoa })
    }

    const handleEditarRole = (pessoa: IPessoa) => {
        setEditandoId(pessoa.id)
        setPessoaEditada({ ...pessoa })
        setEditandoRole(true)
    }

    const handleSalvarRole = () => {
        if (pessoaEditada) {
            PessoasService.updateRole(pessoaEditada.id, pessoaEditada)
            .then((result) => {
                if (result instanceof ApiErrorException) {
                    alert(result.message)
                } else {
                    console.log(pessoaEditada)
                    setListaPessoas(prev =>
                        prev.map(p =>
                            p.id === editandoId ? { ...p, ...pessoaEditada } as IPessoa : p
                        )
                    )
                }
            })
        setEditandoId(null)
        setEditandoRole(false)
        }
    }

    const handleSalvar = () => {
        if (pessoaEditada) {
            PessoasService.atualizar(pessoaEditada.id, pessoaEditada)
            .then((result) => {
                if (result instanceof ApiErrorException) {
                    alert(result.message)
                } else {
                    setListaPessoas(prev =>
                        prev.map(p =>
                            p.id === editandoId ? { ...p, ...pessoaEditada } as IPessoa : p
                        )
                    )
                }
            })
        setEditandoId(null)
        }
    }

    const handleChange = (campo: keyof IPessoa, valor: string) => {
        if (pessoaEditada) {
            setPessoaEditada({ ...pessoaEditada, [campo]: valor })
        }
    }

    const handleRemover = (pessoa: IPessoa) => {
        if (pessoa) {
            PessoasService.deletar(pessoa.id)
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
                        <th>Email</th>
                        <th>Cargo</th>
                    </tr>
                </thead>
                <tbody>
                    {listaPessoas.map(pessoa => (
                        <tr key={pessoa.id}>
                            <th scope="row">{pessoa.id}</th>
                            <td>
                                {editandoId === pessoa.id ? (
                                    <input
                                        value={pessoaEditada?.name || ''}
                                        onChange={(e) => handleChange('name', e.target.value)}
                                    />
                                ) : (
                                    pessoa.name
                                )}
                            </td>
                            <td>
                                {editandoId === pessoa.id ? (
                                    <input
                                        value={pessoaEditada?.email || ''}
                                        onChange={(e) => handleChange('email', e.target.value)}
                                    />
                                ) : (
                                    pessoa.email
                                )}
                            </td>
                            <td>
                                {(editandoId === pessoa.id && editandoRole === true) ? (
                                    <input
                                        value={pessoaEditada?.role || ''}
                                        onChange={(e) => handleChange('role', e.target.value)}
                                    />
                                ) : (
                                    pessoa.role.toUpperCase()
                                )}
                            </td>
                            <td>
                                {editandoId === pessoa.id ? (
                                    <button onClick={handleSalvar}>Salvar</button>
                                ) : (
                                    <button onClick={() => handleEditar(pessoa)}>Editar</button>
                                )}
                            </td>
                            <td>
                                {(editandoId === pessoa.id && editandoRole === true) ? (
                                    <button onClick={handleSalvarRole}>Salvar</button>
                                ) : (
                                    <button onClick={() => handleEditarRole(pessoa)}>Editar Role</button>
                                )}
                            </td>
                            <td><button onClick={() => handleRemover(pessoa)}>Remover</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    )
}
