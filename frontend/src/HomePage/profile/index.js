import React from 'react';
import { connect } from 'react-redux';
import { history } from '../../_helpers';
import { userActions } from '../../_actions';
import { userService } from '../../_services';


class profile extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            name: '',
            surname: '',
            bio: '',
            submitted: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmit(e) {
        e.preventDefault();
        this.setState({ submitted: true });
        const { name,surname,bio } = this.state;
        this.props.editProfile(name,surname,bio);
        
    }

    render() {
    
    }

}
function mapState(state) {
    const { loggingIn } = state.authentication;
    return { loggingIn };
}

const actionCreators = {
   editProfile: userActions.editprofile,
   getProfile: userService.getProfile
};

const connectedLoginPage = connect(mapState, actionCreators)(LoginPage);
export { connectedLoginPage as Login };