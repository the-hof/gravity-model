select b.timestep_id as t,
b.Name,
(b.x / 147100000000 ) as unit_x,
(b.y / 147100000000) as unit_y,
|/((b.x / 147100000000)^2 + (b.y / 147100000000)^2) as unit_r,
(b.vx / 29805.5) as unit_vx,
(b.vy/ 29805.5) as unit_vy,
|/((b.vx/29805.5)^2 + (b.vy/29805.5)^2) as v,
(fx/3.66471830844725e+22) as unit_fx,
(fy/3.66471830844725e+22) as unit_fy,
|/((fx/3.66471830844725e+22) ^ 2 + (fy/3.66471830844725e+22) ^2) as f,
f.type as Force_Type
from Body b
left join Force f on ((b.id = f.this_body_id) and (b.timestep_id = f.timestep_id))
where b.timestep_id is not null
and b.name in ('Earth')
order by b.timestep_id, b.name