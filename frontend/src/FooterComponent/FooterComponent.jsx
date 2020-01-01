import React from 'react';
import { connect } from 'react-redux';
import { Layout } from 'antd';
const {  Footer } = Layout;

class FooterComponent extends React.Component {
  render() {
    return (
      <Footer style={{ textAlign: 'center' }}>
      YALLP ©2019 Created by three awesome front-end developers.
      </Footer>
    );
  }
}

function mapState(state) {
    return {  };
}

const actionCreators = {
}

const connectedFooterComponent = connect(mapState, actionCreators)(FooterComponent);
export { connectedFooterComponent as FooterComponent };