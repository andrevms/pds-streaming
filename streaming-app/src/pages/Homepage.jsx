import "./Homepage.css";
import ContentLine from "../components/ContentLine";

export default function Homepage() {
    return (
        <div className="homepage">
            <div className="section">
                <p className="section-title">Séries</p>
                <ContentLine mediaType="tvshow" />
            </div>
            <div className="section">
                <p className="section-title">Filmes</p>
                <ContentLine mediaType="movie" />
            </div>
        </div>
    );
}