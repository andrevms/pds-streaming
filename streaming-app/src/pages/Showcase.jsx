import { useOutletContext } from "react-router-dom";
import "./Showcase.css";
import { useEffect, useState } from "react";

export default function Showcase(props) {
    const [data, setData] = useState();

    const { updateTitle } = useOutletContext();

    useEffect(() => {

    })

    return (
        <div>
            
        </div>
    );
}