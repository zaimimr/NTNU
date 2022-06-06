import json
import math
import periodictable
from mendeleev import element

atom_data = {}
calculate_atom_data = {}
calculate_density = lambda m, r : ( 3 * m ) / ( 4 * math.pi * r ** 3 )
calculate_distance = lambda p, m : ( 10**7 ) / ((( p * periodictable.constants.avogadro_number ) / ( m )) ** ( 1 / 3 ))

with open('radii.json', 'r') as f:
    radii_data = json.load(f)
    for radii in radii_data:
        atom_data[radii["atomic_number"]] = {
            "atomic_number": radii["atomic_number"],
            "name": radii["name"],
            "symbol": radii["symbol"],
            "empirical": radii["empirical"] if radii["empirical"] != "no data" else None,
            "calculated": radii["Calculated"] if radii["Calculated"] != "no data" else None,
            "molar_mass": periodictable.elements[radii["atomic_number"]].mass
        }
f.close()

with open('density.json', 'r') as f:
    density_data = json.load(f)
    for density in density_data:
        if density["atomic_number"] in atom_data:
            atom_data[density["atomic_number"]]["density"] = density["density"] if density["density"] != "Unknown" else None
f.close()



for atomic_number in atom_data:
  try: 
    empirical_density = calculate_density(atom_data[atomic_number]["molar_mass"], atom_data[atomic_number]["empirical"]) 
  except: 
    empirical_density = "Could not calculate empirical density"
  try: 
    calculated_density = calculate_density(atom_data[atomic_number]["molar_mass"], atom_data[atomic_number]["calculated"]) 
  except: 
    calculated_density = "Could not calculate calculated density"
  try: 
    compare_empirical_density = calculate_density(atom_data[atomic_number]["molar_mass"], atom_data[atomic_number]["empirical"]) - atom_data[atomic_number]["density"] 
  except: 
    compare_empirical_density = "Could not calculate compare empirical density"
  try: 
    compare_calculated_density = calculate_density(atom_data[atomic_number]["molar_mass"], atom_data[atomic_number]["calculated"]) - atom_data[atomic_number]["density"]
  except: 
    compare_calculated_density = "Could not calculate compare calculated density"
  try: 
    calculated_distance = calculate_distance(atom_data[atomic_number]["density"], atom_data[atomic_number]["molar_mass"])
  except: 
    calculated_distance = "Could not calculate distance"

  calculate_atom_data[atomic_number] = {
    "empirical_density": empirical_density,
    "calculated_density": calculated_density,
    "compare_empirical_density": compare_empirical_density,
    "compare_calculated_density": compare_calculated_density,
    "calculated_distance": calculated_distance
  }

with open('calculated.json', 'w') as outfile:
    json.dump(calculate_atom_data, outfile)

