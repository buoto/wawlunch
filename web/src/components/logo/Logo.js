import React, { PureComponent } from 'react';

import logo from "../../assets/icons/logo.svg"

import "./Logo.css";

class Logo extends PureComponent {
    render() {
        return (
            <div className="logo">
                <img src={logo} className="logo__body"/>
                <h1 className="logo__name">Zlunchowani</h1>
            </div>
        );
    }
}

export default Logo;
