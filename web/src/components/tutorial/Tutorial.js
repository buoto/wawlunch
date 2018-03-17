import React, { PureComponent } from 'react';
import Step from "./step/Step";

import phone from "../../assets/icons/phone.svg";
import map from "../../assets/icons/map.svg";
import cutlery from "../../assets/icons/cutlery.svg";
import creditCard from "../../assets/icons/credit-card.svg";
import arrow1 from "../../assets/icons/arrow1.svg"
import arrow2 from "../../assets/icons/arrow2.svg"
import arrow3 from "../../assets/icons/arrow3.svg"

import "./Tutorial.css";

class Tutorial extends PureComponent {
    render() {
        return (
            <div className="tutorial">
                <Step icon={phone} arrow={arrow1}>
                    Pobierz aplikację
                </Step>
                <Step icon={map} arrow={arrow2}>
                    Znajdź lokal na lunch
                </Step>
                <Step icon={cutlery} arrow={arrow3}>
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
