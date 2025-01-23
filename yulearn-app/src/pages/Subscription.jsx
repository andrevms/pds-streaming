import React, { useEffect, useState } from "react";
import { useNavigate, useOutletContext } from "react-router-dom";
import "./Subscription.css";

export default function Subscription() {
    const [cardNumber, setCardNumber] = useState("");
    const [cardHolder, setCardHolder] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    const [cvv, setCvv] = useState("");
    const [error, setError] = useState("");
    const [subscriptionPlan, setSubscriptionPlan] = useState("PREMIUM_MONTHLY");
    const [successMessage, setSuccessMessage] = useState("");
    const [isSubscribed, setIsSubscribed] = useState(false); // State para controlar se a assinatura foi bem-sucedida

    const { updateTitle } = useOutletContext();
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!cardNumber || !cardHolder || !expiryDate || !cvv) {
            setError("Por favor, preencha todos os campos.");
            return;
        }

        const requestBody = {
            username: localStorage.getItem("username"),
            subscriptionType: subscriptionPlan,
            creditCardDTO: {
                cardHolderName: cardHolder,
                cardNumber: cardNumber,
                expirationDate: expiryDate,
                cvv: cvv,
            },
        };

        fetch("http://localhost:8080/api/subscriptions", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestBody),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                setSuccessMessage("Assinatura iniciada com sucesso!");
                setIsSubscribed(true); // Define o estado como verdadeiro para mostrar o botão de login
            })
            .catch((error) => {
                console.error("Error:", error);
                setError("Houve um erro ao processar a assinatura.");
            })
            .finally(() => {
                setCardHolder("");
                setCardNumber("");
                setExpiryDate("");
                setCvv("");
            });

        console.log("Payment processed:", { cardNumber, cardHolder, expiryDate, cvv, subscriptionPlan });
        setError("");
    };

    useEffect(() => {
        document.title = `Iniciar Assinatura | Streaming`;
        updateTitle(`Iniciar Assinatura`);
    });

    const handleLoginRedirect = () => {
        navigate("/login");
    };

    return (
        <div className="subscription-content">
            <p className="price-text">Por R$29,99 por mês você tem acesso a todo o conteúdo sem anúncios.</p>
            <div className="outer-payment-form">
                <form onSubmit={handleSubmit}>
                    <div className="input-box">
                        <label className="input-label">Nome completo</label>
                        <input
                            className="large-subscription-input"
                            type="text"
                            value={cardHolder}
                            onChange={(e) => setCardHolder(e.target.value)}
                            placeholder="John Doe"
                            required
                        />
                    </div>
                    <div className="input-box">
                        <label className="input-label">Número do cartão</label>
                        <input
                            className="large-subscription-input"
                            type="text"
                            value={cardNumber}
                            onChange={(e) => setCardNumber(e.target.value)}
                            placeholder="1234 5678 9012 3456"
                            required
                        />
                    </div>
                    <div className="input-box">
                        <label className="input-label">Data de vencimento</label>
                        <input
                            className="small-subscription-input"
                            type="text"
                            value={expiryDate}
                            onChange={(e) => setExpiryDate(e.target.value)}
                            placeholder="MM/YY"
                            required
                        />
                    </div>
                    <div className="input-box">
                        <label className="input-label">CVV</label>
                        <input
                            className="small-subscription-input"
                            type="text"
                            value={cvv}
                            onChange={(e) => setCvv(e.target.value)}
                            placeholder="123"
                            required
                        />
                    </div>

                    <div className="input-box">
                        <label className="input-label">Escolha seu plano</label>
                        <select
                            value={subscriptionPlan}
                            onChange={(e) => setSubscriptionPlan(e.target.value)}
                            className="subscription-select"
                        >
                            <option value="PREMIUM_MONTHLY">Mensal - R$ 29,99</option>
                            <option value="PREMIUM_ANNUALLY">Anual - R$ 299,99</option>
                        </select>
                    </div>

                    {error && <p style={{ color: "red" }}>{error}</p>}
                    {successMessage && <p style={{ color: "green" }}>{successMessage}</p>} {/* Exibe mensagem de sucesso */}

                    {!isSubscribed ? (
                        <div className="outer-submit-button">
                            <button className="submit-button" type="submit">
                                Iniciar Assinatura
                            </button>
                        </div>
                    ) : (
                        <div className="outer-submit-button">
                            <button className="submit-button" type="button" onClick={handleLoginRedirect}>
                                Ir para login
                            </button>
                        </div>
                    )}
                </form>
            </div>
        </div>
    );
}
