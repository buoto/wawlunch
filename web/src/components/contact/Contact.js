import React, { PureComponent } from 'react';
import { Link } from 'react-router-dom'

import "./Contact.css";

class Contact extends PureComponent {
    render() {
        return (
            <div className="contact">
                <h2>Współpraca</h2>
                <p>Masz już konto? <Link to='/form' className="contact__link">Dodaj nowy lunch</Link>.</p>
                <p>Prowadzisz restaurację? Bądźmy w kontakcie.</p>
                <div className="contact__form">
                    <div className="contact__form--columns">
                        <div className="contact__form--row">
                            <input placeholder="Nazwa restauracji" />
                            <input placeholder="Imię i nazwisko" />
                            <input placeholder="Adres email" />
                            <input placeholder="Numer telefonu" />
                        </div>
                        <textarea placeholder="Wiadomość" className="contact__form--textarea" />
                    </div>
                    <button className="contact__form--button">Wyślij</button>
                </div>
            </div>
        );
    }
}

export default Contact;
