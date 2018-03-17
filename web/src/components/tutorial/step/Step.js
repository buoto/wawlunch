import React, { PureComponent } from 'react';

import arrow from "../../../assets/icons/arrow.svg";

import "./Step.css";

class Step extends PureComponent {
    render() {
        const { children, icon } = this.props;

        return (
            <div className="step">
                <img src={icon} className="step__icon" />
                {children}
                <img src={arrow} className="step__arrow" />
            </div>
        );
    }
}

export default Step;
