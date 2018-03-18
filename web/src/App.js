import React, { PureComponent } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom'

import Landing from "./components/landing/Landing";
import Form from "./components/form/Form";

import './App.css';

class App extends PureComponent {
  render() {
    return (
      <div className="app">
          <Router>
              <div>
                  <Route exact path="/" component={Landing}/>
                  <Route path="/form" component={Form}/>
              </div>
          </Router>
      </div>
    );
  }
}

export default App;
