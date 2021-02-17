import React from "react";
import ReactDOM from "react-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import {
  BrowserRouter,
  Redirect,
  Route,
  Switch,
  useRouteMatch,
} from "react-router-dom";

import Dashboard from "components/Dashboard";
import Pods from "components/Pods";

import Layout from "./components/Layout";

import "antd/dist/antd.css";
import "./index.css";

const queryClient = new QueryClient();

const Kub = () => {
  const { path } = useRouteMatch();
  return (
    <Switch>
      <Route path={`${path}/pods`}>
        <Pods />
      </Route>
      <Redirect to={`${path}/pods`} />
    </Switch>
  );
};

ReactDOM.render(
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Layout>
        <Switch>
          <Route path="/kubernetes">
            <Kub />
          </Route>
          <Route path="/">
            <Dashboard />
          </Route>
          <Redirect to="/" />
        </Switch>
      </Layout>
    </BrowserRouter>
  </QueryClientProvider>,
  document.getElementById("root")
);
