import React from "react";
import { Link, useRouteMatch } from "react-router-dom";
import { x } from "@xstyled/emotion";

import QueryRequest from "../QueryRequest";
import { QueryFields } from "../types";

import Pod from "./Pod";

const KubQueryDetail: React.FC<{ field: QueryFields }> = ({ field }) => {
  const {
    params: { detailName },
  } = useRouteMatch<{ detailName: string }>();

  if (field !== "pods") {
    return <div>fields other than pods are not supported yet</div>;
  }

  return (
    <>
      <x.div p={20} bg="white">
        <Link to={`/kubernetes/${field}`}>{field}</Link> {">"}{" "}
        <Link to={`/kubernetes/${field}/${detailName}`}>{detailName}</Link>
      </x.div>
      <x.div p={20}>
        <QueryRequest
          type="detail"
          component={({ data: _data }) => {
            const data = _data[detailName];
            return <Pod data={data} />;
          }}
          field={field}
          name={detailName}
        />
      </x.div>
    </>
  );
};

export default KubQueryDetail;
