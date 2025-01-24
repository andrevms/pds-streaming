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
        } else if (mediaType == `music`) {
            document.title = `Músicas`;
        } else if (mediaType == `podcast`) {
            document.title = `Podcasts`;
        } else {
            document.title = `Catálogo`;
        }
    });

    return (
        <div className="catalog-page">
            {(mediaType == `all` || mediaType == `music`) && <div className="section">
                <p className="section-title">Músicas</p>
                <ContentLine mediaType="music" />
            </div>}
            {(mediaType == `all` || mediaType == `podcast`) && <div className="section">
                <p className="section-title">Podcasts</p>
                <ContentLine mediaType="podcast" />
            </div>}
        </div>
    );
}