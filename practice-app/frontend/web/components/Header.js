import Link from 'next/link'

const linkStyle = {
  marginRight: 15
}

const Header = () => (
  <div>
    <Link href="/">
      <a style={linkStyle}>Home</a>
    </Link>
    <Link href="/synonym">
      <a style={linkStyle}>Synonym</a>
    </Link>
    <Link href="/antonym">
      <a style={linkStyle}>Antonym</a>
    </Link>
  </div>
)

export default Header
