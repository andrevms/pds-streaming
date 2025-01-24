import { useEffect } from "react";
import "./LandingPage.css";
import { Link, useOutletContext } from "react-router-dom";

export default function LandingPage() {
    const { updateTitle } = useOutletContext();

    useEffect(() => {
        document.title = `Blockfy`;
        updateTitle(`Olá, seja bem-vindo`);
    })

    return (
        <div className="landing-page">
            <div>
                <p className="landing-label">Já é assinante? Faça login na sua conta</p>
                <div className="outer-landing-button">
                    <button className="landing-button"><Link className="link" to="/login">Fazer Login</Link></button>
                </div>
                <p className="ou-label">O U</p>
                <p className="landing-label">Crie sua conta e assine o Blockfy por apenas R$9,90 por mês</p>
                <div className="outer-landing-button">
                    <button className="landing-button"><Link className="link" to="/signup">Criar Conta</Link></button>
                </div>
                <p className="ou-label">O U</p>
                <p className="landing-label">Veja nosso catálogo gratuitamente e volte aqui para assinar quando quiser</p>
                <div className="outer-landing-button">
                    <button className="landing-button"><Link className="link" to="/catalog/all">Ver catálogo</Link></button>
                </div>
            </div>
        </div>
    );
}