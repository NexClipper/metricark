import React from "react";
import { x } from "@xstyled/emotion";
import pb from "pretty-bytes";

import { UtilizationGauge } from "components/common/Visualization";

const Utilization: React.FC<{
  used: number;
  total: number;
  usage: number;
  gaugeTitle: string;
  isByte: boolean;
}> = ({ used, total, usage, gaugeTitle, isByte }) => (
  <>
    <UtilizationGauge title={gaugeTitle} value={usage} />
    <x.div row textAlign="center" mt={40} justifyContent="center">
      <x.div col={1 / 4}>
        <x.div fontWeight="bold">{isByte ? pb(used) : used}</x.div>
        <x.div>Used</x.div>
      </x.div>
      <x.div col={1 / 4}>
        <x.div fontWeight="bold">{isByte ? pb(total) : total}</x.div>
        <x.div>Total</x.div>
      </x.div>
    </x.div>
  </>
);

export default Utilization;
