import React, { PureComponent } from 'react';
import Tutorial from "./components/tutorial/Tutorial";

import './App.css';

class App extends PureComponent {
  render() {
    return (
      <div className="app">
          <Tutorial />
      </div>
    );
  }
}

export default App;
