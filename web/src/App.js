import React, { PureComponent } from 'react';
import Tutorial from "./components/tutorial/Tutorial";
import Mobile from "./components/mobile/Mobile";

import './App.css';

class App extends PureComponent {
  render() {
    return (
      <div className="app">
          <Mobile />
          <Tutorial />
      </div>
    );
  }
}

export default App;
