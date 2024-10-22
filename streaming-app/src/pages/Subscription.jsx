import React, { useEffect, useState } from "react";
import { useOutletContext, useNavigate } from "react-router-dom";
import "./Subscription.css";

export default function Subscription() {
    const [cardNumber, setCardNumber] = useState("");
    const [cardHolder, setCardHolder] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    const [cvv, setCvv] = useState("");
    const [error, setError] = useState("");

    const { updateTitle } = useOutletContext();

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!cardNumber || !cardHolder || !expiryDate || !cvv) {
            setError("Por favor, preencha todos os campos.");
            return;
        }
        console.log("Payment processed:", { cardNumber, cardHolder, expiryDate, cvv });
        setError("");
    };

    useEffect(() => {
        document.title = `Iniciar Assinatura | Streaming`;
        updateTitle(`Iniciar Assinatura`);
    });

    return (
        <div className="subscription-content">
            <p className="price-text">Por R$29,99 por mês você tem acesso a todo o conteúdo sem anúncios.</p>
            <div className="outer-payment-form">
                <form onSubmit={handleSubmit}>
                    <div className="input-box">
                        <label className="input-label">Nome completo</label>
                        <input className="large-subscription-input" type="text" value={cardHolder} onChange={(e) => setCardHolder(e.target.value)} placeholder="John Doe" required />
                    </div>
                    <div className="input-box">
                        <label className="input-label">Número do cartão</label>
                        <input className="large-subscription-input" type="text" value={cardNumber} onChange={(e) => setCardNumber(e.target.value)} placeholder="1234 5678 9012 3456" required />
                    </div>
                    <div className="input-box">
                        <label className="input-label">Data de vencimento</label>
                        <input className="small-subscription-input" type="text" value={expiryDate} onChange={(e) => setExpiryDate(e.target.value)} placeholder="MM/YY" required />
                    </div>
                    <div className="input-box">
                        <label className="input-label">CVV</label>
                        <input className="small-subscription-input" type="text" value={cvv} onChange={(e) => setCvv(e.target.value)} placeholder="123" required />
                    </div>
                    {error && <p style={{ color: "red" }}>{error}</p>}
                    <div className="outer-submit-button">
                        <button className="submit-button" type="submit">
                            Iniciar Assinatura
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
