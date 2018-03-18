import React, { PureComponent } from 'react';

import Tutorial from "../tutorial/Tutorial";
import Mobile from "../mobile/Mobile";
import Contact from "../contact/Contact";
import Footer from "../footer/Footer";
import About from "../about/About";

import arrow from "../../assets/icons/arrow.svg"

import "./Landing.css";
import Logo from "../logo/Logo";

class Landing extends PureComponent {
    render() {
        return (
            <div className="landing">
                <div className="landing__view">
                    <Mobile />
                    <div className="landing__view--rows">
                        <Logo />
                        <Tutorial />
                    </div>
                </div>
                <div className="landing__info">
                    <img src={arrow} className="landing__arrow" />
                    <div className="landing__info--columns">
                        <About />
                        <Contact />
                    </div>
                    <Footer />
                </div>
            </div>
        );
    }
}

export default Landing;
