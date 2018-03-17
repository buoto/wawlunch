import React, { PureComponent } from 'react';

import "./Step.css";

class Step extends PureComponent {
    render() {
        const { children, icon, arrow } = this.props;

        return (
            <div className="step">
                <img src={icon} className="step__icon" />
                {children}
                {!arrow || <img src={arrow} className="step__arrow" />}
            </div>
        );
    }
}

export default Step;
