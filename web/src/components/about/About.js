import React, { PureComponent } from 'react';

import "./About.css";

class About extends PureComponent {
    render() {
        return (
            <div className="about">
                <h2>O nas</h2>
                <p>Zlunchowani pomaga odkrywać nowe miejsca do zjedzenia lunchu w Warszawie i szybko porównywać menu na
                    dany dzień.
                </p>
                <p>
                    Dzięki czytnikowi kart NFC możesz zamówić lunch już w drodze do restauracji, a gdy dotrzesz na
                    miejsce, twój posiłek będzie już czekał na Ciebie!
                </p>
            </div>
        );
    }
}

export default About;
