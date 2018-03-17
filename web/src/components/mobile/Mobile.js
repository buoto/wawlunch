import React, { PureComponent } from 'react';

import phone from '../../assets/images/phone.png';
import badge from '../../assets/images/google-play-badge.png';

import './Mobile.css';

class Mobile extends PureComponent {
    render() {
        return (
            <div className="mobile">
                <img src={phone} className="mobile__device"/>
                <img src={badge} className="mobile__badge"/>
            </div>
        );
    }
}

export default Mobile;
