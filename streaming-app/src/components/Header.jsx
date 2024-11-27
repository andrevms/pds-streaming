import "./Header.css";
import Logotype from "./Logotype";

export default function Header() {
    return (
        <div className="header">
            <div className="header-logotype">
                <Logotype letterocolor={`#111111`} />
            </div>
            <div className="header-buttons">
                <button className="header-button">Em destaque</button>
                <button className="header-button">Séries</button>
                <button className="header-button">Filmes</button>
                <button className="header-button">Meu perfil</button>
            </div>
        </div>
    );
}