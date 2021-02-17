import React from "react";
import ReactDOM from "react-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";

import Dashboard from "components/Dashboard";
import KubQuery from "components/KubQuery";

import Layout from "./components/Layout";

import "antd/dist/antd.css";
import "./index.css";

const queryClient = new QueryClient();

ReactDOM.render(
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Layout>
        <Switch>
          <Route path="/kubernetes">
            <KubQuery />
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
