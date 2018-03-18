import React, { PureComponent } from 'react';
import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';
import { SingleDatePicker } from 'react-dates';
import moment from 'moment';
import 'moment/locale/pl';

import Section from "./section/Section";
import Footer from "../footer/Footer";

import logoWhite from "../../assets/icons/logo-white.svg";

import "./Form.css";

class Form extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {date: moment(), focused: false};
    }

    render() {
        const { date, focused } = this.state;

        return (
            <div className="form">
                <div className="form__header">
                    <img src={logoWhite} className="form__logo" />
                    <h1 className="form__name">Zlunchowani</h1>
                </div>
                <div className="form__body">
                    <h2 className="form__title">Uzupełnij menu</h2>
                    <div className="section">
                        <label className="section__title">Data</label>
                        <SingleDatePicker
                            date={date}
                            onDateChange={(date) => this.setState({ date })}
                            focused={focused}
                            onFocusChange={({ focused }) => this.setState({ focused })}
                            numberOfMonths={1}
                            displayFormat="dddd, DD.MM.Y"
                        />
                    </div>
                    <Section title="Zupa" />
                    <Section title="Drugie danie" amount={3} />
                    <Section title="Deser" />
                    <Section title="Napój" />
                </div>
                <button className="form__button">
                    Wprowadź
                </button>
                <Footer />
            </div>
        );
    }
}

export default Form;
