import React, { useState } from "react";
import "./Logotype.css";

export default function Logotype(props) {
    const [isHovered, setIsHovered] = useState(false);
    const color = isHovered ? "#d1c602" : "#fff100";

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <svg viewBox="0 0 153.905 48" xmlns="http://www.w3.org/2000/svg">
            <g transform="matrix(1, 0, 0, 1, -104.663849, -9.314347)">
                <rect onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} x="108.9" y="12.9" width="18.7" height="18.7" style={{ stroke: "rgb(0, 0, 0)", strokeWidth: "0px", fill: color }}></rect>
                <rect onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} x="127.564" y="31.614" width="39.3" height="21" style={{ stroke: "rgb(0, 0, 0)", strokeWidth: "0px", fill: color }}></rect>
                <text onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} style={{ fill: props.letterocolor, fontFamily: "Outfit", fontSize: "28px", fontWeight: "600", whiteSpace: "pre" }} y="-0.144">
                    <tspan x="128.364" y="51.909" style={{ fontSize: "28px", wordSpacing: "0px" }}>YU</tspan>
                    <tspan style={{ fill: color, fontSize: "28px", wordSpacing: "0px" }}>LEARN</tspan>
                </text>
            </g>
        </svg>
    );
}
