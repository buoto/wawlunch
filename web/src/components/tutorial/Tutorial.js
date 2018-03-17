import React, { PureComponent } from 'react';
import Step from "./step/Step";

class Tutorial extends PureComponent {
    render() {
        return (
            <div className="tutorial">
                <Step>
                    Pobierz aplikację
                </Step>
                <Step>
                    Znajdź lokal na lunch
                </Step>
                <Step>
                    Delektuj się smakiem
                </Step>
                <Step>
                    Zapłać telefonem
                </Step>
            </div>
        );
    }
}

export default Tutorial;
