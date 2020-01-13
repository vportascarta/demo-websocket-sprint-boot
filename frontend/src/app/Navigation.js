import React, { useState } from "react";
import { Link } from "react-router-dom";
import * as ROUTES from "./Routes";
import { makeStyles } from "@material-ui/core";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Drawer from "@material-ui/core/Drawer";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import Divider from "@material-ui/core/Divider";
import ListItemText from "@material-ui/core/ListItemText";
import IconButton from "@material-ui/core/IconButton";
import BusinessIcon from "@material-ui/icons/Business";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";
import HomeIcon from "@material-ui/icons/Home";
import InfoIcon from '@material-ui/icons/Info';
import MenuIcon from "@material-ui/icons/Menu";
import PeopleIcon from "@material-ui/icons/People";
import ScheduleIcon from "@material-ui/icons/Schedule";

const drawerWidth = 250;
const useStyles = makeStyles(theme => ({
  root: {
    display: "flex"
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0
  },
  drawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-end"
  },
  drawerPaper: {
    width: drawerWidth
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3)
  },
  title: {
    flexGrow: 1
  }
}));

const Navigation = () => {
  const classes = useStyles();
  const [isDrawerOpened, setDrawerOpened] = useState(false);

  let navigation = [];
  navigation.push(NavigationMenu);

  return (
    <div className={classes.root}>
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <IconButton
            onClick={() => setDrawerOpened(!isDrawerOpened)}
            color="inherit"
            aria-label="Menu"
          >
            <MenuIcon />
          </IconButton>
          <Typography
            variant="h6"
            color="inherit"
            noWrap
            className={classes.title}
          >
            Test App
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        open={isDrawerOpened}
        onClose={() => setDrawerOpened(false)}
        className={classes.drawer}
        classes={{
          paper: classes.drawerPaper
        }}
      >
        <div className={classes.drawerHeader}>
          <IconButton onClick={() => setDrawerOpened(false)}>
            {classes.direction === "ltr" ? (
              <ChevronRightIcon />
            ) : (
              <ChevronLeftIcon />
            )}
          </IconButton>
        </div>
        <Divider />
        {navigation.map((group, idx) => (
          <div key={idx}>
            <List>
              {group.map(elem => (
                <ListItem
                  button
                  key={elem.name}
                  onClick={() => setDrawerOpened(false)}
                  component={Link}
                  to={elem.to}
                >
                  <ListItemIcon>{elem.icon}</ListItemIcon>
                  <ListItemText primary={elem.name} />
                </ListItem>
              ))}
            </List>
            <Divider />
          </div>
        ))}
      </Drawer>
    </div>
  );
};

const NavigationMenu = [
  { name: "Accueil", icon: <HomeIcon />, to: ROUTES.WELCOME },
  // { name: "Planning", icon: <ScheduleIcon />, to: ROUTES.SCHEDULER },
  { name: "Employés", icon: <PeopleIcon />, to: ROUTES.EMPLOYEE },
  // { name: "Hiérarchie", icon: <BusinessIcon />, to: ROUTES.HIERARCHY },
  // { name: "Infos", icon: <InfoIcon />, to: ROUTES.INFOS }
];

export default Navigation;
