import React, { Component } from 'react';

import "./Section.css";

class Section extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { title, amount = 1 } = this.props;

        return (
            <div className="section">
                <label className="section__title">{title}</label>
                {amount >= 1 && <input className="form__input" />}
                {[...Array(amount - 1)].map((value, key) => <input key={key} className="form__input" placeholder="opcjonalne" />)}
            </div>
        );
    }
}

export default Section;
