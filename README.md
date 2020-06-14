# Pool App
Summer project for an app (currently intended for android only) that helps with balancing the chemical balance of pools based off of their current chem readings.


# Goals
<ol>
  <li>Have user input a pool's PH, chlorine, alkalinity, calcium hardness, and other readings.</li>
  <li>Implement the taylor watergram wheel (the logic used behind it) for pool diagnostics.</li>
  <li>Implement the charts in the back of the talor book.</li>
  <li>Implement a tool that estimates the volume of a pool based on its shape.</li>
  <li>Create different types of user profiles (this would track the history of a pool's chemistry over time and would also track the treatments performed towards the pool).
      <ul>
        <li>Owner of pool: only tracks the history of a single pool</li>
        <li>Pool Technition: tracks history of any number of pools.</li>
      </ul>
  </li>
</ol>
  
# General Notes for Pools
<ul>
  <li> If high amounts of hardness and alkalinity are combined, high concentrations of calcium carbonate may result in scale formation or cloudy water.</li>
  <li> When cal hypo is added to a pool, pH, alkalinity and hardness of treated water may drift upward.</li>
  <li>Use the Taylor watergram wheel to calculate the SI of a pool based off of its current chemical balance. If a pool's SI is outside of the range (-0.3 < SI < 0.5), adjust the wheel's readings (PH, ALK, CALCIUM, TEMP) in ways that make sense (don't set temp to 50, or PH to 6, or any crazy shit; remember calcium hardness can only be reduced by draining a pool and refiniling it) until the pool falls into the range of a balanced SI. Add chems to properly set the pool's chemistry based on the results found from the SI test.</li>
  <ul>
    <li> SI >= 0.5: water may become cloudy or desposit scale</li>
    <li> SI <= -0.3: water may become corrosive to concrete, plaster, or metal surfaces.<li>
  </ul>
  <li>Total Alkalinity (carbonite Total Alkalinity) = Alkalinity - (1/3 * Cyanuric acid)</li>
  <li></li>
  <li></li>
</ul>
  
