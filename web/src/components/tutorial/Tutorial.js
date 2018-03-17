import React, { PureComponent } from 'react';
import Step from "./step/Step";

import phone from "../../assets/icons/phone.svg";
import map from "../../assets/icons/map.svg";
import cutlery from "../../assets/icons/cutlery.svg";
import creditCard from "../../assets/icons/credit-card.svg";

import "./Tutorial.css";

class Tutorial extends PureComponent {
    render() {
        return (
            <div className="tutorial">
                <Step icon={phone}>
                    Pobierz aplikację
                </Step>
                <Step icon={map}>
                    Znajdź lokal na lunch
                </Step>
                <Step icon={cutlery}>
                    Delektuj się smakiem
                </Step>
                <Step icon={creditCard}>
                    Zapłać telefonem
                </Step>
            </div>
        );
    }
}

export default Tutorial;
