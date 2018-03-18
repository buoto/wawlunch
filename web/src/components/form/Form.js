import React, { Component } from 'react';
import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';
import { SingleDatePicker } from 'react-dates';
import moment from 'moment';
import 'moment/locale/pl';
import Modal from 'react-modal';
import { Link } from 'react-router-dom';

import Section from "./section/Section";
import Footer from "../footer/Footer";

import logoWhite from "../../assets/icons/logo-white.svg";
import ok from "../../assets/icons/ok.svg";

import "./Form.css";

class Form extends Component {
    constructor(props) {
        super(props);

        this.state = { date: moment(), focused: false, dishes: [], isModalOpen: false };
    }

    submit = (event) => {
        event.preventDefault();

        const { date, dishes } = this.state;

        fetch('http://buoto.me:8080/menus/', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                placeId: 2,
                price: 2000,
                date: date.format("YYYY-MM-DD"),
                dishes: dishes,
            }),
        })

        this.openModal();
    }

    handle = (data) => {
        this.setState(({ dishes }) => {
            const d = dishes.find((dish) => dish.id === data.id && data.type === dish.type);

            if (d) {
                d.name = data.name;
            } else {
                dishes.push(data);
            }

            return dishes;
        });
    }

    closeModal() {
        this.setState({isModalOpen: false});
        window.location.reload()
    }

    openModal() {
        this.setState({isModalOpen: true});
    }

    render() {
        const { date, focused, isModalOpen } = this.state;

        const customStyles = {
            content : {
                top                   : '50%',
                left                  : '50%',
                right                 : 'auto',
                bottom                : 'auto',
                marginRight           : '-50%',
                transform             : 'translate(-50%, -50%)'
            }
        };

        return (
            <div className="form">
                <Modal
                    contentLabel="Example Modal"
                    style={customStyles}
                    isOpen={isModalOpen}
                    onRequestClose={this.closeModal.bind(this)}
                >
                    <div className="modal">
                        <h2 className="modal__text">Dodano menu</h2>
                        <div className="modal__icon--wrapper">
                            <img src={ok} className="modal__icon" />
                        </div>
                    </div>
                </Modal>
                <div className="form__header">
                    <Link to="/" className="form__header">
                        <img src={logoWhite} className="form__logo" />
                        <h1 className="form__name">Zlunchowani</h1>
                    </Link>
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
                    <Section title="Zupa" type="soup" handle={this.handle} />
                    <Section title="Drugie danie" amount={3} type="main" handle={this.handle} />
                    <Section title="Deser" type="dess" handle={this.handle} />
                    <Section title="Napój" type="drin" handle={this.handle} />
                </div>
                <button className="form__button" onClick={(e) => this.submit(e)}>
                    Wprowadź
                </button>
                <Footer />
            </div>
        );
    }
}

export default Form;
