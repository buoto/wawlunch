import React, { Component } from 'react';

import "./Section.css";

class Section extends Component {
    constructor(props) {
        super(props);
    }

    returnDish = (value, key) => {
        const { type, handle } = this.props;
        handle({
            id: key,
            name: value,
            type: type,
            isVege: false,
        });
    }

    render() {
        const { title, amount = 1, handle } = this.props;

        return (
            <div className="section">
                <label className="section__title">{title}</label>
                {
                    amount >= 1 &&
                    <input className="form__input" onChange={e => this.returnDish(e.target.value, amount - 1)} required />
                }
                {[...Array(amount - 1)].map((value, key) => (
                    <input key={key} className="form__input" placeholder="opcjonalne" onChange={(e) => this.returnDish(e.target.value, key)} />)
                )}
            </div>
        );
    }
}

export default Section;
