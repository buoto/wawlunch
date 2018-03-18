import React, { PureComponent } from 'react';

import "./Showcase.css";

class Showcase extends PureComponent {
    render() {
        return (
            <div className="showcase">
                <h2>Kontakt</h2>
                <p>Aplikacja powstała w ramach projektu na hackathonie Wawcode 2018</p>
                <p>Projekt zrealizowany przez zespół "Zróbmy lunche" w składzie:</p>
                <p>Adrianna Małkiewicz, Krzysztof Antoniak, Marcin Wierzbiński, Marta Mazurkiewicz, Michał Błotniak</p>
            </div>
        );
    }
}

export default Showcase;
