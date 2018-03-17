import React, { PureComponent } from 'react';

import Tutorial from "./components/tutorial/Tutorial";
import Mobile from "./components/mobile/Mobile";
import About from "./components/about/About";
import Contact from "./components/contact/Contact";
import Footer from "./components/footer/Footer";

import './App.css';

class App extends PureComponent {
  render() {
    return (
      <div className="app">
          <div className="app__view">
              <Mobile />
              <Tutorial />
          </div>
          <div className="app__info">
              <div className="app__info--columns">
                  <About />
                  <Contact />
              </div>
              <Footer />
          </div>
      </div>
    );
  }
}

export default App;
