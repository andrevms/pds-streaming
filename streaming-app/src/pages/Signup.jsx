import React, { useEffect, useState } from "react";
import { useOutletContext, useNavigate } from "react-router-dom";

export default function Signup() {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");

    const handleSubmit = (event) => {};
    const { updateTitle } = useOutletContext();

    useEffect(() => {
        document.title = `Criar Conta | Streaming`;
        updateTitle(`Criar Conta`);
    });

    return (
        <div className="outer-login-form">
            <form className="login-form" onSubmit={handleSubmit}>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">Nome de usuário</label>
                    <input className="signup-input" type="text" id="username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">E-mail</label>
                    <input className="signup-input" type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="password">Senha</label>
                    <input className="signup-input" type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="password">Repetir senha</label>
                    <input className="signup-input" type="password" id="repeated-password" name="repeated-password" value={repeatedPassword} onChange={(e) => setRepeatedPassword(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">Primeiro nome</label>
                    <input className="signup-input" type="text" id="first-name" name="first-name" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">Sobrenome</label>
                    <input className="signup-input" type="text" id="last-name" name="last-name" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
                </div>
                <div className="outer-submit-button">
                    <button className="submit-button" type="submit">Criar Conta</button>
                </div>
            </form>
        </div>
    );
}