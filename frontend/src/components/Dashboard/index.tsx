import React from "react";
import { x } from "@xstyled/emotion";

import { Area1, Area2, Area3 } from "./Area";

const AreaRow: React.FC = ({ children }) => (
  <x.div row pb={40}>
    {children}
  </x.div>
);

function Dashboard() {
  return (
    <x.div p={20}>
      <AreaRow>
        <x.div col>
          <Area1 />
        </x.div>
      </AreaRow>
      <AreaRow>
        <Area2 />
      </AreaRow>
      <AreaRow>
        <Area3 />
      </AreaRow>
    </x.div>
  );
}

export default Dashboard;
