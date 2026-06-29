import React from 'react';

function App() {
  return (
    <div className="app-container">
      <header className="header">
        <div className="logo-section">
          <span className="logo-text">MERGE</span>
          <span className="badge">MVP Stage</span>
        </div>
        <nav className="nav">
          <span className="nav-item active">Dashboard</span>
          <span className="nav-item">Curriculum</span>
          <span className="nav-item">Drills</span>
          <span className="nav-item">Profile</span>
        </nav>
      </header>

      <main className="main-content">
        <section className="welcome-banner">
          <h1>Welcome to Merge Engineering Formation</h1>
          <p>
            Transforming university students into trusted software engineers through deliberate 3.5-year training.
          </p>
        </section>

        <div className="grid">
          <div className="card">
            <h3>Scout Stage</h3>
            <p>Understand who you are as a learner through a 3-layer conceptual probe assessment.</p>
            <button className="btn btn-primary">Start Assessment</button>
          </div>

          <div className="card">
            <h3>Cadet Stage</h3>
            <p>Build automaticity, fix broken code patterns, and submit drills evaluated by Judge0.</p>
            <button className="btn btn-secondary" disabled>Locked (complete Scout)</button>
          </div>

          <div className="card">
            <h3>Engineering Competency</h3>
            <p>Measure your skills across 8 industry-standard dimensions mapped to SFIA.</p>
            <button className="btn btn-secondary">View Framework</button>
          </div>
        </div>
      </main>

      <footer className="footer">
        <p>&copy; 2026 Merge Platform. Confidential &bull; Semicolon Africa Capstone Project.</p>
      </footer>
    </div>
  );
}

export default App;
