import './Hero.css';

function Hero() {
  return (
    <section className="hero">
      <div className="hero__content">
        <h1 className="hero__heading">Welcome to Car Clinic</h1>
        <p className="hero__sub">
          Manage vehicles, appointments, and service records all in one place.
        </p>
        <div className="hero__cta">
          <a href="#" className="btn btn--hero-primary">Book Appointment</a>
          <a href="#" className="btn btn--hero-outline">Learn More</a>
        </div>
      </div>
    </section>
  );
}

export default Hero;
