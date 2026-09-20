import './Footer.css';

function Footer() {
  const year = new Date().getFullYear();
  return (
    <footer className="footer">
      <p>&copy; {year} Car Clinic. All rights reserved.</p>
    </footer>
  );
}

export default Footer;
