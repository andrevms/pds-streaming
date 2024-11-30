import React, { useEffect } from "react";
import { useOutletContext, useParams } from "react-router-dom";
import "./Catalog.css";
import ContentLine from "../components/ContentLine";

export default function Catalog() {
    const { mediaType } = useParams();

    const { updateTitle } = useOutletContext();

    useEffect(() => {
        updateTitle("Catálogo");

        if (mediaType == `trending`) {
            updateTitle("Em destaque");
            document.title = `Em destaque`;
        } else if (mediaType == `tvshows`) {
            document.title = `Séries`;
        } else if (mediaType == `movies`) {
            document.title = `Filmes`;
        } else {
            document.title = `Catálogo`;
        }
    });

    return (
        <div className="catalog-page">
            {(mediaType == `all` || mediaType == `tvshows`) && <div className="section">
                <p className="section-title">Séries</p>
                <ContentLine mediaType="tvshow" />
            </div>}
            {(mediaType == `all` || mediaType == `movies`) && <div className="section">
                <p className="section-title">Filmes</p>
                <ContentLine mediaType="movie" />
            </div>}
        </div>
    );
}