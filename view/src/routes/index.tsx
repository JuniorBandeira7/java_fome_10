import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import {Login, Home, Users, Produtcs, Cadastrar, CadastrarProduto} from "../pages";

export const AppRoutes = () => {
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/usuarios" element={<Users/>}/>
                <Route path="/produtos" element={<Produtcs/>}/>
                <Route path="/cadastrar" element={<Cadastrar/>}/>
                <Route path="/cadastrar-produto" element={<CadastrarProduto/>}/>
                <Route path="/" element={<Home/>}/>
                <Route path="*"  element={<Navigate to={"/"}/>}/> 
            </Routes>
        </BrowserRouter>
    )
}