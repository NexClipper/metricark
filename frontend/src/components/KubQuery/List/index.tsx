import React from "react";

import QueryRequest from "../QueryRequest";
import { QueryFields } from "../types";

import PodsList from "./Pods";

const Logger: React.FC<{ data: any }> = ({ data }) => {
  return <pre>{JSON.stringify(data, null, 4)}</pre>;
};

const KubQueryList: React.FC<{ field: QueryFields }> = ({ field }) => {
  if (field === "pods") {
    return <QueryRequest component={PodsList} field={field} />;
  }

  return <QueryRequest component={Logger} field={field} />;
};

export default KubQueryList;
