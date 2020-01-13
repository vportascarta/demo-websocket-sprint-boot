import React, {useState} from "react";
import * as ROUTES from "./Routes";
import CssBaseline from "@material-ui/core/CssBaseline";
import deepOrange from "@material-ui/core/colors/deepOrange";
import green from "@material-ui/core/colors/green";
import Navigation from "./Navigation";
import Page404 from "./404";
import WelcomePage from "./Welcome";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {createMuiTheme, makeStyles} from "@material-ui/core";
import {ThemeProvider} from "@material-ui/styles";
import Alert from "../contexts/Alert/Alert";
import AlertContext from "../contexts/Alert/context";
import EmployeeList from "../employee/EmployeeList";
import EditEmployee from "../employee/EditEmployee";

const theme = createMuiTheme({
  palette: {
    primary: {main: green[700]},
    secondary: {main: deepOrange[700]},
    type: "dark"
  },
  direction: "ltr"
});

const useStyles = makeStyles(theme => ({
  root: {
    height: "100vh"
  },
  content: {
    marginTop: `calc(${theme.mixins.toolbar.minHeight}px + 5vh)`,
    marginLeft: "5vw",
    marginRight: "5vw"
  },
  loader: {
    marginTop: "calc(50vh - 40px)",
    marginLeft: "calc(50vw - 40px)"
  }
}));

const Application = () => {
  const classes = useStyles();
  const [alertData, setAlertData] = useState(null);

  return (
    <ThemeProvider theme={theme}>
      <AlertContext.Provider value={{alertData, setAlertData}}>
        <div className={classes.root}>
          <Router>
            <CssBaseline/>
            <Navigation/>
            <div className={classes.content}>
              <Switch>
                <Route
                  exact
                  path={ROUTES.WELCOME}
                  component={WelcomePage}
                />
                <Route
                  exact
                  path={ROUTES.EMPLOYEE}
                  component={EmployeeList}
                />
                <Route
                  exact
                  path={ROUTES.EMPLOYEE_EDIT}
                  component={EditEmployee}
                />
                <Route exact component={Page404}/>
              </Switch>
            </div>
          </Router>
        </div>
      </AlertContext.Provider>
      {alertData !== null ? (
        <Alert
          open={true}
          duration={5000}
          variant={alertData.variant}
          message={alertData.message}
          onClose={() => setAlertData(null)}
        />
      ) : null}
    </ThemeProvider>
  );
};

export default Application;
