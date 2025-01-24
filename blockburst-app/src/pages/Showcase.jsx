import { useOutletContext } from "react-router-dom";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Showcase.css";

export default function Showcase(props) {
    const [tvShow, setTvShow] = useState();

    const { tvShowId } = useParams();

    const { updateTitle } = useOutletContext();
    
    useEffect(() => {
        updateTitle(props.title);
    })

    return (
        <div>
            <img></img>
        </div>
    );
}